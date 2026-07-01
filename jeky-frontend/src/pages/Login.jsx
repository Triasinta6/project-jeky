import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";

function Login() {
    const navigate = useNavigate();
    const { login } = useAuth();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setLoading(true);

        try {
            await login(email, password);
            navigate("/dashboard");
        } catch (err) {
            console.log(err);

            if (err.response?.data) {
                setError(typeof err.response.data === "string" ? err.response.data : err.response.data.message);
            } else {
                setError("Gagal login. Cek backend atau koneksi API.");
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div style={styles.page}>
            <div style={styles.card}>
                <h1 style={styles.title}>Jeky Admin</h1>
                <p style={styles.subtitle}>Login untuk masuk ke dashboard admin</p>

                {error && <div style={styles.error}>{error}</div>}

                <form onSubmit={handleSubmit}>
                    <div style={styles.formGroup}>
                        <label style={styles.label}>Email</label>
                        <input
                            style={styles.input}
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="admin@jeky.com"
                        />
                    </div>

                    <div style={styles.formGroup}>
                        <label style={styles.label}>Password</label>
                        <input
                            style={styles.input}
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Masukkan password"
                        />
                    </div>

                    <button style={styles.button} type="submit" disabled={loading}>
                        {loading ? "Loading..." : "Login"}
                    </button>
                </form>
            </div>
        </div>
    );
}

const styles = {
    page: {
        minHeight: "100vh",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        background: "#f3f4f6",
        fontFamily: "Arial, sans-serif",
    },
    card: {
        width: "100%",
        maxWidth: "400px",
        background: "#ffffff",
        padding: "32px",
        borderRadius: "16px",
        boxShadow: "0 10px 30px rgba(0,0,0,0.08)",
    },
    title: {
        margin: 0,
        fontSize: "28px",
        fontWeight: "700",
        color: "#111827",
    },
    subtitle: {
        marginTop: "8px",
        marginBottom: "24px",
        color: "#6b7280",
        fontSize: "14px",
    },
    formGroup: {
        marginBottom: "16px",
    },
    label: {
        display: "block",
        marginBottom: "8px",
        fontSize: "14px",
        fontWeight: "600",
        color: "#374151",
    },
    input: {
        width: "100%",
        padding: "12px 14px",
        borderRadius: "10px",
        border: "1px solid #d1d5db",
        fontSize: "14px",
        boxSizing: "border-box",
    },
    button: {
        width: "100%",
        padding: "12px 14px",
        borderRadius: "10px",
        border: "none",
        background: "#111827",
        color: "#ffffff",
        fontWeight: "700",
        cursor: "pointer",
        fontSize: "14px",
    },
    error: {
        background: "#fee2e2",
        color: "#991b1b",
        padding: "10px 12px",
        borderRadius: "10px",
        marginBottom: "16px",
        fontSize: "14px",
    },
};

export default Login;