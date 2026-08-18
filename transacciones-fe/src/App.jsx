import { useState } from "react";
import Login from "./components/Login";
import RegistrarOperacion from "./components/RegistrarOperacion";

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  if (!isAuthenticated) {
    return <Login onLogin={setIsAuthenticated} />;
  }

  return (
    <div className="min-h-screen bg-gray-50 p-4">
      <div className="container mx-auto">
        <div className="flex justify-end mb-4">
          <button
            onClick={() => setIsAuthenticated(false)}
            className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-lg text-sm"
          >
            Cerrar Sesión
          </button>
        </div>
        <RegistrarOperacion />
      </div>
    </div>
  );
}

export default App;
