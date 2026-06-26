import { useEffect, useState } from "react";
import api from "../api";
import PageHeader from "../components/PageHeader";

const statuses = ["WAITING", "ACCEPTED", "ON_PROGRESS", "COMPLETED", "CANCELLED"];

function Orders() {
  const [orders, setOrders] = useState([]);
  const [selectedOrder, setSelectedOrder] = useState(null);
  const [selectedStatus, setSelectedStatus] = useState("");
  const [isStatusModalOpen, setIsStatusModalOpen] = useState(false);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const loadOrders = () => {
    api.get("/orders")
      .then((response) => {
        setOrders(response.data);
        setError("");
      })
      .catch(() => setError("Gagal ambil data order."));
  };

  useEffect(() => {
    loadOrders();
  }, []);

  const openStatusModal = (order) => {
    setSelectedOrder(order);
    setSelectedStatus(order.status || "WAITING");
    setMessage("");
    setError("");
    setIsStatusModalOpen(true);
  }

  const closeStatusModal = () => {
    setIsStatusModalOpen(false);
    setSelectedOrder(null);
    setSelectedStatus("");
  };

  const updateStatus = async (event) => {
    event.preventDefault();

    if (!selectedOrder) return;

    setMessage("");
    setError("");

    try {
      await api.put(`/orders/${selectedOrder.id}/status`, null, {
        params: { status: selectedStatus },
      });

      setMessage("Status order berhasil diupdate.");
      closeStatusModal();
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
        title="Data Order"
        description="Pantau dan update status order customer Jeky."
        breadcrumb={{
          parentPath: "/",
          parentLabel: "Dashboard",
          currentLabel: "Orders",
        }}
      />

      {message && <div className="alert alert-success">{message}</div>}
      {error && <div className="alert alert-danger">{error}</div>}

      <section className="card">
        <div className="section-header">
          <div>
            <h2>Order Masuk</h2>
          </div>
        </div>

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
                <th className="text-center">Aksi</th>
              </tr>
            </thead>

            <tbody>
              {orders.length === 0 ? (
                <tr>
                  <td colSpan="8" className="empty-table">
                    Belum ada order.
                  </td>
                </tr>
              ) : (
                orders.map((order) => (
                  <tr key={order.id}>
                    <td>{order.id}</td>
                    <td>{order.layanan?.nama || "-"}</td>
                    <td>{order.customerName || "-"}</td>
                    <td>{order.phoneNumber || "-"}</td>
                    <td className="wrap">{order.pickupAddress || "-"}</td>
                    <td className="wrap">{order.destinationAddress || "-"}</td>
                    <td>
                      <span className={`badge ${badgeClass(order.status)}`}>
                        {formatStatus(order.status)}
                      </span>
                    </td>
                    <td>
                      <div className="icon-action-row">
                        <button
                          className="icon-btn icon-edit"
                          title="Update status order"
                          onClick={() => openStatusModal(order)}
                        >
                          <EditIcon />
                        </button>

                        <button
                          className="icon-btn icon-delete"
                          title="Hapus order"
                          onClick={() => deleteOrder(order.id)}
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

      {isStatusModalOpen && selectedOrder && (
        <div className="modal-backdrop">
          <div className="modal-card">
            <div className="modal-header">
              <div>
                <h2>Update Status Order</h2>
                <p>
                  Order #{selectedOrder.id} - {selectedOrder.customerName || "Customer"}
                </p>
              </div>

              <button className="modal-close" onClick={closeStatusModal}>
                ×
              </button>
            </div>

            <form onSubmit={updateStatus}>
              <div className="order-detail-box">
                <div>
                  <span>Layanan</span>
                  <strong>{selectedOrder.layanan?.nama || "-"}</strong>
                </div>

                <div>
                  <span>Jemput</span>
                  <strong>{selectedOrder.pickupAddress || "-"}</strong>
                </div>

                <div>
                  <span>Tujuan</span>
                  <strong>{selectedOrder.destinationAddress || "-"}</strong>
                </div>
              </div>

              <div className="form-group">
                <label>Status Order</label>
                <select
                  value={selectedStatus}
                  onChange={(event) => setSelectedStatus(event.target.value)}
                >
                  {statuses.map((status) => (
                    <option key={status} value={status}>
                      {formatStatus(status)}
                    </option>
                  ))}
                </select>
              </div>

              <div className="modal-actions">
                <button className="btn btn-light" type="button" onClick={closeStatusModal}>
                  Batal
                </button>

                <button className="btn btn-primary" type="submit">
                  Update
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
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

function formatStatus(status) {
  if (!status) return "-";

  return status
    .replace("_", " ")
    .toLowerCase()
    .replace(/\b\w/g, (char) => char.toUpperCase());
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

export default Orders;