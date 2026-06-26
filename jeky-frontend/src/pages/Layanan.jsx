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
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const loadLayanan = () => {
    api.get("/layanan")
      .then((response) => {
        setLayananList(response.data);
        setError("");
      })
      .catch(() => setError("Gagal ambil data layanan."));
  };

  useEffect(() => {
    loadLayanan();
  }, []);

  const openAddModal = () => {
    setEditingId(null);
    setForm(emptyForm);
    setMessage("");
    setError("");
    setIsModalOpen(true);
  };

  const openEditModal = (layanan) => {
    setEditingId(layanan.id);
    setForm({
      nama: layanan.nama || "",
      deskripsi: layanan.deskripsi || "",
      hargaDasar: layanan.hargaDasar || "",
      aktif: layanan.aktif ?? true,
    });
    setMessage("");
    setError("");
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setEditingId(null);
    setForm(emptyForm);
  };

  const handleChange = (event) => {
    const { name, value, type, checked } = event.target;

    setForm((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setMessage("");
    setError("");

    if (!form.nama.trim()) {
      setError("Nama layanan wajib diisi.");
      return;
    }

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

      closeModal();
      loadLayanan();
    } catch (err) {
      setError("Gagal simpan layanan. Cek kembali inputan data.");
    }
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
        title="Kelola Data Layanan"
        breadcrumb={{
          parentPath: "/",
          parentLabel: "Dashboard",
          currentLabel: "Layanan",
        }}
      />

      {message && <div className="alert alert-success">{message}</div>}
      {error && <div className="alert alert-danger">{error}</div>}

      <section className="card">
        <div className="section-header">
          <div>
            <h2>Daftar Layanan</h2>
          </div>

          <button className="btn btn-primary btn-add" onClick={openAddModal}>
            <span className="plus-icon">+</span>
            Tambah Layanan
          </button>
        </div>

        <div className="table-wrap">
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Nama</th>
                <th>Deskripsi</th>
                <th>Harga</th>
                <th>Status</th>
                <th className="text-center">Aksi</th>
              </tr>
            </thead>

            <tbody>
              {layananList.length === 0 ? (
                <tr>
                  <td colSpan="6" className="empty-table">
                    Belum ada layanan.
                  </td>
                </tr>
              ) : (
                layananList.map((layanan) => (
                  <tr key={layanan.id}>
                    <td>{layanan.id}</td>
                    <td>{layanan.nama}</td>
                    <td className="wrap">{layanan.deskripsi}</td>
                    <td>{formatRupiah(layanan.hargaDasar)}</td>
                    <td>
                      <span className={layanan.aktif ? "badge badge-active" : "badge badge-inactive"}>
                        {layanan.aktif ? "Aktif" : "Nonaktif"}
                      </span>
                    </td>
                    <td>
                      <div className="icon-action-row">
                        <button
                          className="icon-btn icon-edit"
                          title="Edit layanan"
                          onClick={() => openEditModal(layanan)}
                        >
                          <EditIcon />
                        </button>

                        <button
                          className="icon-btn icon-delete"
                          title="Hapus layanan"
                          onClick={() => handleDelete(layanan.id)}
                        >
                          <TrashIcon />
                        </button>
                      </div>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </section>

      {isModalOpen && (
        <div className="modal-backdrop">
          <div className="modal-card">
            <div className="modal-header">
              <div>
                <h2>{editingId ? "Edit Layanan" : "Tambah Layanan"}</h2>
              </div>

              <button className="modal-close" onClick={closeModal}>
                ×
              </button>
            </div>

            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label>Nama Layanan</label>
                <input
                  name="nama"
                  value={form.nama}
                  onChange={handleChange}
                  placeholder="Contoh: Jeky Ride"
                  required
                />
              </div>

              <div className="form-group">
                <label>Deskripsi</label>
                <textarea
                  name="deskripsi"
                  value={form.deskripsi}
                  onChange={handleChange}
                  placeholder="Contoh: Layanan antar jemput motor"
                />
              </div>

              <div className="form-group">
                <label>Harga Dasar</label>
                <input
                  name="hargaDasar"
                  type="number"
                  value={form.hargaDasar}
                  onChange={handleChange}
                  placeholder="Contoh: 8000"
                />
              </div>

              <label className="checkbox-row">
                <input
                  name="aktif"
                  type="checkbox"
                  checked={form.aktif}
                  onChange={handleChange}
                />
                Aktif
              </label>

              <div className="modal-actions">
                <button className="btn btn-light" type="button" onClick={closeModal}>
                  Batal
                </button>

                <button className="btn btn-primary" type="submit">
                  {editingId ? "Update" : "Simpan"}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
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

function EditIcon() {
  return (
    <svg width="17" height="17" viewBox="0 0 24 24" fill="none">
      <path
        d="M16.862 4.487L19.5 7.125M18.188 3.162C18.916 2.434 20.096 2.434 20.824 3.162C21.552 3.89 21.552 5.07 20.824 5.798L7.5 19.122L3.75 20.25L4.878 16.5L18.188 3.162Z"
        stroke="currentColor"
        strokeWidth="2"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
}

function TrashIcon() {
  return (
    <svg width="17" height="17" viewBox="0 0 24 24" fill="none">
      <path
        d="M6 7H18M9 7V5C9 4.44772 9.44772 4 10 4H14C14.5523 4 15 4.44772 15 5V7M10 11V17M14 11V17M8 7L9 20H15L16 7"
        stroke="currentColor"
        strokeWidth="2"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
}

export default Layanan;