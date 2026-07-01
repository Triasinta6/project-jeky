import { createContext, useContext, useEffect, useState } from "react";
import api from "../api/api";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null);
    const [token, setToken] = useState(localStorage.getItem("token"));
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const checkLogin = async () => {
            if (!token) {
                setLoading(false);
                return;
            }

            try {
                const response = await api.get("/auth/me");
                setUser(response.data);
            } catch (error) {
                localStorage.removeItem("token");
                setToken(null);
                setUser(null);
            } finally {
                setLoading(false);
            }
        };

        checkLogin();
    }, [token]);

    const login = async (username, password) => {
        const response = await api.post("/auth/login", {
            username,
            password,
        });

        localStorage.setItem("token", response.data.token);
        setToken(response.data.token);

        setUser({
            id: response.data.id,
            username: response.data.username,
            role: response.data.role,
        });

        return response.data;
    };

    const logout = () => {
        localStorage.removeItem("token");
        setToken(null);
        setUser(null);
    };

    return (
        <AuthContext.Provider
            value={{
                user,
                token,
                loading,
                login,
                logout,
                isAuthenticated: !!user,
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    return useContext(AuthContext);
}