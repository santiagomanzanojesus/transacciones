import { AuthProvider, useAuth } from "./components/AuthContext";
import Login from "./components/Login";
import RegistrarOperacion from "./components/RegistrarOperacion";
import ListadoOperaciones from "./components/ListadoOperaciones";
import { useState, React } from "react";
import { useNavigate } from "react-router-dom";
const AppContent = () => {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();
  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    setUser(null);
    navigate("/login");
  };
  const { isAuthenticated, logout } = useAuth();
  const refrescarLista = () => {
    cargarTransacciones({
      page: parametros.page,
      limit: parametros.limit,
      search: parametros.search,
      sortBy: parametros.sortBy,
      sortDir: parametros.sortDir,
    });
  };

  if (!isAuthenticated) {
    return <Login />;
  }

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="max-w-7xl mx-auto">
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-2xl font-bold">Operaciones</h1>
          <button
            onClick={logout}
            className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded"
          >
            Cerrar Sesión
          </button>
        </div>
        <div className="grid grid-cols-1 gap-6">
          <RegistrarOperacion
            onRegistroExitoso={refrescarLista}
            onLogout={handleLogout}
            onRegistroExitoso={() => obtenerTransacciones()}
          />
          <ListadoOperaciones />
        </div>
      </div>
    </div>
  );
};

function App() {
  return (
    <AuthProvider>
      <AppContent />
    </AuthProvider>
  );
}

const cargarTransacciones = async (params = {}) => {
  setLoading(true);
  setError(null);

  try {
    const parametros = {
      page: params.page ?? paginacion.page,
      limit: params.limit ?? paginacion.limit,
      search: params.search ?? paginacion.search,
      sortBy: params.sortBy ?? paginacion.sortBy,
      sortDir: params.sortDir ?? paginacion.sortDir,
    };

    const response = await obtenerTransacciones(parametros);

    setTransacciones(response.content || []);

    setPaginacion({
      ...parametros,
      totalPages: response.totalPages || 0,
      totalElements: response.totalElements || 0,
    });
  } catch (err) {
    console.error("Error al cargar transacciones:", err);
    setError("No se pudieron cargar las transacciones. Intenta nuevamente.");
    if (err.response?.status === 401) {
      // logout();
    }
  } finally {
    setLoading(false);
  }
};

export default App;
