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
import api from "../api";

function Dashboard() {
  const [stats, setStats] = useState(null);
  const [error, setError] = useState("");
  const [startDate, setStartDate] = useState("2026-06-25");
  const [endDate, setEndDate] = useState("2026-06-25");

  useEffect(() => {
    loadDashboard();
  }, []);

  function loadDashboard() {
    setError("");

    api.get("/dashboard/stats")
      .then((response) => setStats(response.data))
      .catch(() => {
        setError("Gagal ambil data dashboard.");
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
      <div className="dashboard-title-row">
        <div>
          <h1>Dashboard Jeky</h1>
        </div>

        <div className="breadcrumb">
          Dashboard / Overview
        </div>
      </div>

      <section className="filter-card">
        <h3>Filter Periode</h3>

        <div className="filter-row">
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

          <button onClick={loadDashboard}>Filter</button>
        </div>
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