import { useEffect, useState } from "react";
import api from "../api";
import PageHeader from "../components/PageHeader";

const emptyForm = {
  nama: "",
  deskripsi: "",
  hargaDasar: "",
  aktif: true,
};

function Layanan() {
  const [layananList, setLayananList] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [editingId, setEditingId] = useState(null);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const loadLayanan = () => {
    api.get("/layanan")
      .then((response) => setLayananList(response.data))
      .catch(() => setError("Gagal ambil data layanan."));
  };

  useEffect(() => {
    loadLayanan();
  }, []);

  const handleChange = (event) => {
    const { name, value, type, checked } = event.target;
    setForm((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const resetForm = () => {
    setForm(emptyForm);
    setEditingId(null);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setMessage("");
    setError("");

    const payload = {
      nama: form.nama,
      deskripsi: form.deskripsi,
      hargaDasar: Number(form.hargaDasar || 0),
      aktif: form.aktif,
    };

    try {
      if (editingId) {
        await api.put(`/layanan/${editingId}`, payload);
        setMessage("Layanan berhasil diupdate.");
      } else {
        await api.post("/layanan", payload);
        setMessage("Layanan berhasil ditambahkan.");
      }
      resetForm();
      loadLayanan();
    } catch (err) {
      setError("Gagal simpan layanan. Cek kembali input dan backend.");
    }
  };

  const handleEdit = (layanan) => {
    setEditingId(layanan.id);
    setForm({
      nama: layanan.nama || "",
      deskripsi: layanan.deskripsi || "",
      hargaDasar: layanan.hargaDasar || "",
      aktif: layanan.aktif ?? true,
    });
  };

  const handleDelete = async (id) => {
    if (!confirm("Hapus layanan ini?")) return;
    setMessage("");
    setError("");

    try {
      await api.delete(`/layanan/${id}`);
      setMessage("Layanan berhasil dihapus.");
      loadLayanan();
    } catch (err) {
      setError("Gagal hapus layanan. Bisa jadi layanan masih dipakai order.");
    }
  };

  return (
    <>
      <PageHeader
        kicker="Management"
        title="Kelola Layanan"
        description="Tambah, edit, dan hapus layanan Jeky."
      />

      {message && <div className="alert alert-success">{message}</div>}
      {error && <div className="alert alert-danger">{error}</div>}

      <section className="content-grid">
        <div className="card">
          <h2>{editingId ? "Edit Layanan" : "Tambah Layanan"}</h2>
          <p className="card-desc">Data akan disimpan lewat API Spring Boot.</p>

          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>Nama Layanan</label>
              <input name="nama" value={form.nama} onChange={handleChange} placeholder="Contoh: Jeky Ride" required />
            </div>

            <div className="form-group">
              <label>Deskripsi</label>
              <textarea name="deskripsi" value={form.deskripsi} onChange={handleChange} placeholder="Contoh: Layanan antar jemput motor" />
            </div>

            <div className="form-group">
              <label>Harga Dasar</label>
              <input name="hargaDasar" type="number" value={form.hargaDasar} onChange={handleChange} placeholder="Contoh: 8000" />
            </div>

            <label className="checkbox-row">
              <input name="aktif" type="checkbox" checked={form.aktif} onChange={handleChange} />
              Aktif
            </label>

            <div className="button-row">
              <button className="btn btn-primary" type="submit">{editingId ? "Update" : "Simpan"}</button>
              {editingId && <button className="btn btn-light" type="button" onClick={resetForm}>Batal</button>}
            </div>
          </form>
        </div>

        <div className="card">
          <h2>Daftar Layanan</h2>
          <p className="card-desc">Semua layanan dari database Jeky.</p>

          <div className="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Nama</th>
                  <th>Deskripsi</th>
                  <th>Harga</th>
                  <th>Status</th>
                  <th>Aksi</th>
                </tr>
              </thead>
              <tbody>
                {layananList.length === 0 ? (
                  <tr><td colSpan="6">Belum ada layanan.</td></tr>
                ) : layananList.map((layanan) => (
                  <tr key={layanan.id}>
                    <td>{layanan.id}</td>
                    <td>{layanan.nama}</td>
                    <td className="wrap">{layanan.deskripsi}</td>
                    <td>{formatRupiah(layanan.hargaDasar)}</td>
                    <td><span className={layanan.aktif ? "badge badge-active" : "badge badge-inactive"}>{layanan.aktif ? "Aktif" : "Nonaktif"}</span></td>
                    <td>
                      <div className="button-row">
                        <button className="btn btn-small" onClick={() => handleEdit(layanan)}>Edit</button>
                        <button className="btn btn-small btn-danger" onClick={() => handleDelete(layanan.id)}>Hapus</button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </section>
    </>
  );
}

function formatRupiah(value) {
  return new Intl.NumberFormat("id-ID", {
    style: "currency",
    currency: "IDR",
    maximumFractionDigits: 0,
  }).format(value || 0);
}

export default Layanan;
