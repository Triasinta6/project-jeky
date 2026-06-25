import { useEffect, useState } from "react";
import api from "../api";
import PageHeader from "../components/PageHeader";

const statuses = ["WAITING", "ACCEPTED", "ON_PROGRESS", "COMPLETED", "CANCELLED"];

function Orders() {
  const [orders, setOrders] = useState([]);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const loadOrders = () => {
    api.get("/orders")
      .then((response) => setOrders(response.data))
      .catch(() => setError("Gagal ambil data order."));
  };

  useEffect(() => {
    loadOrders();
  }, []);

  const updateStatus = async (id, status) => {
    setMessage("");
    setError("");

    try {
      await api.put(`/orders/${id}/status`, null, { params: { status } });
      setMessage("Status order berhasil diupdate.");
      loadOrders();
    } catch (err) {
      setError("Gagal update status order.");
    }
  };

  const deleteOrder = async (id) => {
    if (!confirm("Hapus order ini?")) return;
    setMessage("");
    setError("");

    try {
      await api.delete(`/orders/${id}`);
      setMessage("Order berhasil dihapus.");
      loadOrders();
    } catch (err) {
      setError("Gagal hapus order.");
    }
  };

  return (
    <>
      <PageHeader
        kicker="Orders"
        title="Daftar Order"
        description="Pantau dan update status order customer Jeky."
      />

      {message && <div className="alert alert-success">{message}</div>}
      {error && <div className="alert alert-danger">{error}</div>}

      <div className="card">
        <h2>Order Masuk</h2>
        <p className="card-desc">Data order dari API Spring Boot.</p>

        <div className="table-wrap">
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Layanan</th>
                <th>Customer</th>
                <th>No HP</th>
                <th>Jemput</th>
                <th>Tujuan</th>
                <th>Status</th>
                <th>Aksi</th>
              </tr>
            </thead>
            <tbody>
              {orders.length === 0 ? (
                <tr><td colSpan="8">Belum ada order.</td></tr>
              ) : orders.map((order) => (
                <tr key={order.id}>
                  <td>{order.id}</td>
                  <td>{order.layanan?.nama || "-"}</td>
                  <td>{order.customerName}</td>
                  <td>{order.phoneNumber}</td>
                  <td className="wrap">{order.pickupAddress}</td>
                  <td className="wrap">{order.destinationAddress}</td>
                  <td><span className={`badge ${badgeClass(order.status)}`}>{order.status}</span></td>
                  <td>
                    <div className="order-actions">
                      <select defaultValue={order.status} onChange={(event) => updateStatus(order.id, event.target.value)}>
                        {statuses.map((status) => (
                          <option key={status} value={status}>{status}</option>
                        ))}
                      </select>
                      <button className="btn btn-small btn-danger" onClick={() => deleteOrder(order.id)}>Hapus</button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </>
  );
}

function badgeClass(status) {
  if (status === "WAITING") return "badge-waiting";
  if (status === "ACCEPTED") return "badge-active";
  if (status === "ON_PROGRESS") return "badge-progress";
  if (status === "COMPLETED") return "badge-done";
  if (status === "CANCELLED") return "badge-cancel";
  return "badge-inactive";
}

export default Orders;
