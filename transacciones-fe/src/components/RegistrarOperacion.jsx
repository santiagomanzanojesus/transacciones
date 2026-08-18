import { useState } from "react";
import api from "../api/api";
import { encryptAES } from "../utils/crypto";
import Notificacion from "./Notification";

function RegistrarOperacion() {
  const [form, setForm] = useState({
    operacion: "",
    importe: "",
    cliente: "",
    secreto: "",
  });
  const [responseData, setResponseData] = useState(null);
  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
    // Limpiar errores del campo al cambiar
    setErrors((prev) => ({ ...prev, [name]: "" }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setResponseData(null);
    setErrors({});

    // Cifrar el secreto antes de enviar
    const payload = {
      ...form,
      secreto: encryptAES(form.secreto),
    };

    try {
      const response = await api.post("/api/transacciones", payload);
      setResponseData(response.data);
      // Limpiar formulario (opcional)
      setForm({ operacion: "", importe: "", cliente: "", secreto: "" });
    } catch (err) {
      if (err.response && err.response.status === 400) {
        // Errores de validación del backend
        setErrors(err.response.data);
      } else {
        alert("Error al procesar la transacción. Intente de nuevo.");
      }
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-3xl mx-auto bg-white p-8 rounded-lg shadow-md mt-10">
      <h2 className="text-2xl font-bold text-center mb-6 text-gray-800">
        Registrar Operación
      </h2>
      <form onSubmit={handleSubmit}>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {/* Operación */}
          <div>
            <label className="block text-gray-700 text-sm font-medium mb-1">
              Operación
            </label>
            <input
              type="text"
              name="operacion"
              className={`w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 ${errors.operacion ? "border-red-500" : "border-gray-300"}`}
              placeholder="Ej: venta"
              value={form.operacion}
              onChange={handleChange}
              required
            />
            {errors.operacion && (
              <p className="text-red-500 text-xs mt-1">{errors.operacion}</p>
            )}
          </div>

          {/* Importe */}
          <div>
            <label className="block text-gray-700 text-sm font-medium mb-1">
              Importe
            </label>
            <input
              type="text"
              name="importe"
              className={`w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 ${errors.importe ? "border-red-500" : "border-gray-300"}`}
              placeholder="Ej: 100.00"
              value={form.importe}
              onChange={handleChange}
              required
            />
            {errors.importe && (
              <p className="text-red-500 text-xs mt-1">{errors.importe}</p>
            )}
          </div>

          {/* Cliente */}
          <div>
            <label className="block text-gray-700 text-sm font-medium mb-1">
              Cliente
            </label>
            <input
              type="text"
              name="cliente"
              className={`w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 ${errors.cliente ? "border-red-500" : "border-gray-300"}`}
              placeholder="Ej: Angel"
              value={form.cliente}
              onChange={handleChange}
              required
            />
            {errors.cliente && (
              <p className="text-red-500 text-xs mt-1">{errors.cliente}</p>
            )}
          </div>

          {/* Secreto */}
          <div>
            <label className="block text-gray-700 text-sm font-medium mb-1">
              Secreto
            </label>
            <input
              type="text"
              name="secreto"
              className={`w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 ${errors.secreto ? "border-red-500" : "border-gray-300"}`}
              placeholder="Palabra secreta"
              value={form.secreto}
              onChange={handleChange}
              required
            />
            {errors.secreto && (
              <p className="text-red-500 text-xs mt-1">{errors.secreto}</p>
            )}
          </div>
        </div>

        <button
          type="submit"
          disabled={loading}
          className="mt-6 w-full bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-lg transition duration-200 disabled:opacity-50"
        >
          {loading ? "Enviando..." : "Registrar Transacción"}
        </button>
      </form>

      <Notificacion data={responseData} />
    </div>
  );
}

export default RegistrarOperacion;
