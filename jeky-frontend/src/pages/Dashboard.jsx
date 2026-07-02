import { useEffect, useState } from "react";
import {
  Bar,
  BarChart,
  CartesianGrid,
  Legend,
  Line,
  LineChart,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";
import api from "../api/api";
import PageHeader from "../components/PageHeader";

function Dashboard() {
  const [stats, setStats] = useState(null);
  const [error, setError] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  useEffect(() => {
    loadDashboard();
  }, []);

  function loadDashboard(params = {}) {
    setError("");

    api.get("/dashboard/stats", { params })
      .then((response) => setStats(response.data))
      .catch(() => {
        setError("Gagal ambil data dashboard.");
      });
  }

  function handleFilter(event) {
    event.preventDefault();

    console.log("Filter diklik", { startDate, endDate });

    if (!startDate || !endDate) {
      setError("Tanggal mulai dan tanggal selesai wajib dipilih.");
      return;
    }

    if (startDate > endDate) {
      setError("Tanggal mulai tidak boleh lebih besar dari tanggal selesai.");
      return;
    }

    loadDashboard({
      startDate,
      endDate,
    });
  }

  const statusChartData = stats
    ? [
      { name: "Waiting", total: stats.waitingOrder ?? 0 },
      { name: "Accepted", total: stats.acceptedOrder ?? 0 },
      { name: "On Progress", total: stats.onProgressOrder ?? 0 },
      { name: "Completed", total: stats.completedOrder ?? 0 },
      { name: "Cancelled", total: stats.cancelledOrder ?? 0 },
    ]
    : [];

  const dailyChartData = stats
    ? [
      {
        tanggal: startDate,
        layanan: stats.totalLayanan ?? 0,
        order: stats.totalOrder ?? 0,
      },
    ]
    : [];

  return (
    <>
      <PageHeader
        title="Dashboard Jeky"
        breadcrumb={{
          parentPath: "/",
          parentLabel: "Dashboard",
          currentLabel: "Overview",
        }}
      />

      <section className="filter-card">
        <h3>Filter Periode</h3>

        <form className="filter-row" onSubmit={handleFilter}>
          <div className="filter-group">
            <label>Mulai Tanggal</label>
            <input
              type="date"
              value={startDate}
              onChange={(event) => setStartDate(event.target.value)}
            />
          </div>

          <div className="filter-group">
            <label>Sampai Tanggal</label>
            <input
              type="date"
              value={endDate}
              onChange={(event) => setEndDate(event.target.value)}
            />
          </div>

          <button type="submit" className="btn-filter">
            Filter
          </button>
        </form>
      </section>

      {error && <div className="alert alert-danger">{error}</div>}
      {!stats && !error && <div className="card">Loading dashboard...</div>}

      {stats && (
        <>
          <section className="summary-grid two-card">
            <SummaryCard
              title="Total Layanan"
              value={stats.totalLayanan}
              icon="🛵"
              color="blue"
            />

            <SummaryCard
              title="Total Order"
              value={stats.totalOrder}
              icon="📦"
              color="green"
            />
          </section>

          <section className="chart-grid">
            <div className="chart-card">
              <h3>Status Order</h3>

              <div className="chart-box">
                <ResponsiveContainer width="100%" height="100%">
                  <BarChart data={statusChartData}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" />
                    <YAxis allowDecimals={false} />
                    <Tooltip />
                    <Bar dataKey="total" name="Total Order" />
                  </BarChart>
                </ResponsiveContainer>
              </div>
            </div>

            <div className="chart-card">
              <h3>Tren Layanan & Order</h3>

              <div className="chart-box">
                <ResponsiveContainer width="100%" height="100%">
                  <LineChart data={dailyChartData}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="tanggal" />
                    <YAxis allowDecimals={false} />
                    <Tooltip />
                    <Legend />
                    <Line type="monotone" dataKey="layanan" name="Layanan" />
                    <Line type="monotone" dataKey="order" name="Order" />
                  </LineChart>
                </ResponsiveContainer>
              </div>
            </div>
          </section>
        </>
      )}
    </>
  );
}

function SummaryCard({ title, value, icon, color }) {
  return (
    <div className={`summary-card ${color}`}>
      <div>
        <h2>{value ?? 0}</h2>
        <p>{title}</p>
      </div>

      <span className="summary-icon">{icon}</span>
    </div>
  );
}

export default Dashboard;