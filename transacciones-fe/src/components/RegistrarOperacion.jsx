import React, { useState } from "react";
import { crearTransaccion } from "../api/api";
import { encryptAES } from "../utils/crypto";

const RegistrarOperacion = ({ onRegistroExitoso }) => {
  const [operacion, setOperacion] = useState("");
  const [importe, setImporte] = useState("");
  const [cliente, setCliente] = useState("");
  const [secreto, setSecreto] = useState("");
  const [loading, setLoading] = useState(false);
  const [mensaje, setMensaje] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMensaje("");
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        // onLogout();
        // return;
      }
      const secretoCifrado = encryptAES(secreto);
      const payload = { operacion, importe, cliente, secreto: secretoCifrado };
      const response = await crearTransaccion(payload);
      setOperacion("");
      setImporte("");
      setCliente("");
      setSecreto("");
      setMensaje(
        `Operación registrada: ID ${response.data.id}, Estatus ${response.data.estatus}, Referencia ${response.data.referencia}`,
      );
      if (onRegistroExitoso) onRegistroExitoso(); // para refrescar lista
      // Resetear campos?
    } catch (err) {
      setMensaje(
        "Error al registrar: " + (err.response?.data?.message || err.message),
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bg-white p-6 rounded-lg shadow-md mb-6">
      <h3 className="text-xl font-semibold mb-4">Registrar Operación</h3>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700">
            Operación
          </label>
          <input
            type="text"
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2 focus:ring-blue-500 focus:border-blue-500"
            value={operacion}
            onChange={(e) => setOperacion(e.target.value)}
            required
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">
            Importe
          </label>
          <input
            type="text"
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2 focus:ring-blue-500 focus:border-blue-500"
            value={importe}
            onChange={(e) => setImporte(e.target.value)}
            required
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">
            Cliente
          </label>
          <input
            type="text"
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2 focus:ring-blue-500 focus:border-blue-500"
            value={cliente}
            onChange={(e) => setCliente(e.target.value)}
            required
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">
            Secreto
          </label>
          <input
            type="text"
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2 focus:ring-blue-500 focus:border-blue-500"
            value={secreto}
            onChange={(e) => setSecreto(e.target.value)}
            required
          />
        </div>
        <button
          type="submit"
          disabled={loading}
          className="w-full bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline disabled:opacity-50"
        >
          {loading ? "Enviando..." : "Registrar Operación"}
        </button>
        {mensaje && (
          <div className="mt-4 p-3 bg-blue-100 border border-blue-400 text-blue-700 rounded">
            {mensaje}
          </div>
        )}
      </form>
    </div>
  );
};

export default RegistrarOperacion;
