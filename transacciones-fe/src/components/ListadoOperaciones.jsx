import React, { useState, useEffect } from "react";
import { obtenerTransacciones } from "../api/api";

const ListadoOperaciones = () => {
  const [transacciones, setTransacciones] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const [page, setPage] = useState(1);
  const [limit, setLimit] = useState(20);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  const [search, setSearch] = useState("");
  const [sortBy, setSortBy] = useState("id");
  const [sortDir, setSortDir] = useState("asc");

  const fetchData = async () => {
    setLoading(true);
    setError("");
    try {
      const response = await obtenerTransacciones(
        page,
        limit,
        search,
        sortBy,
        sortDir,
      );
      const data = response.data;
      setTransacciones(data.content || []);
      setTotalPages(data.totalPages || 0);
      setTotalElements(data.totalElements || 0);
    } catch (err) {
      setError("Error al cargar las transacciones.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, [page, limit, search, sortBy, sortDir]);

  const handleSearchChange = (e) => {
    setSearch(e.target.value);
    setPage(1);
  };

  const handleSortChange = (field) => {
    if (sortBy === field) {
      setSortDir(sortDir === "asc" ? "desc" : "asc");
    } else {
      setSortBy(field);
      setSortDir("asc");
    }
    setPage(1);
  };

  const renderSortIcon = (field) => {
    if (sortBy !== field) return null;
    return sortDir === "asc" ? "▲" : "▼";
  };

  return (
    <div className="bg-white p-6 rounded-lg shadow-md">
      <div className="flex flex-wrap items-center justify-between mb-4 gap-4">
        <div className="flex items-center space-x-2">
          <label className="text-sm font-medium">Buscar:</label>
          <input
            type="text"
            className="border border-gray-300 rounded-md p-2 focus:ring-blue-500 focus:border-blue-500"
            value={search}
            onChange={handleSearchChange}
            placeholder="Buscar por operación o cliente..."
          />
        </div>
        <div className="flex items-center space-x-2">
          <label className="text-sm font-medium">Registros por página:</label>
          <select
            className="border border-gray-300 rounded-md p-2"
            value={limit}
            onChange={(e) => setLimit(Number(e.target.value))}
          >
            <option value="5">5</option>
            <option value="20">20</option>
            <option value="50">50</option>
            <option value="100">100</option>
          </select>
        </div>
      </div>

      {loading && <p className="text-gray-500">Cargando...</p>}
      {error && <p className="text-red-500">{error}</p>}

      {!loading && !error && (
        <>
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th
                    className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer hover:bg-gray-100"
                    onClick={() => handleSortChange("id")}
                  >
                    ID {renderSortIcon("id")}
                  </th>
                  <th
                    className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer hover:bg-gray-100"
                    onClick={() => handleSortChange("operacion")}
                  >
                    Operación {renderSortIcon("operacion")}
                  </th>

                  <th
                    className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer hover:bg-gray-100"
                    onClick={() => handleSortChange("estatus")}
                  >
                    Estatus {renderSortIcon("estatus")}
                  </th>
                  <th
                    className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer hover:bg-gray-100"
                    onClick={() => handleSortChange("referencia")}
                  >
                    Referencia {renderSortIcon("referencia")}
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {transacciones.length === 0 ? (
                  <tr>
                    <td
                      colSpan="6"
                      className="px-6 py-4 text-center text-gray-500"
                    >
                      No hay transacciones.
                    </td>
                  </tr>
                ) : (
                  transacciones.map((item) => (
                    <tr key={item.id}>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                        {item.id}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                        {item.operacion}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm">
                        <span
                          className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${item.estatus === "Aprobada" ? "bg-red-100 text-red-800" : "bg-green-100 text-green-800"}`}
                        >
                          {item.estatus}
                        </span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                        {item.referencia}
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>

          {/* Controles de paginación */}
          <div className="flex items-center justify-between mt-4">
            <div className="text-sm text-gray-700">
              Mostrando {(page - 1) * limit + 1} -{" "}
              {Math.min(page * limit, totalElements)} de {totalElements}{" "}
              registros
            </div>
            <div className="flex space-x-2">
              <button
                onClick={() => setPage(page > 1 ? page - 1 : 1)}
                disabled={page === 1}
                className="px-3 py-1 border rounded-md disabled:opacity-50"
              >
                Anterior
              </button>
              <span className="px-3 py-1 border rounded-md bg-gray-100">
                Página {page} de {totalPages || 1}
              </span>
              <button
                onClick={() => setPage(page < totalPages ? page + 1 : page)}
                disabled={page === totalPages || totalPages === 0}
                className="px-3 py-1 border rounded-md disabled:opacity-50"
              >
                Siguiente
              </button>
            </div>
          </div>
        </>
      )}
    </div>
  );
};

export default ListadoOperaciones;
