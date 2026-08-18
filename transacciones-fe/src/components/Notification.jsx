function Notificacion({ data }) {
  if (!data) return null;

  return (
    <div className="bg-green-100 border-l-4 border-green-500 text-green-700 p-4 rounded-md shadow-md mt-4">
      <h3 className="font-bold mb-1">✅ Transacción registrada</h3>
      <p>
        <span className="font-medium">ID:</span> {data.id}
      </p>
      <p>
        <span className="font-medium">Estatus:</span> {data.estatus}
      </p>
      <p>
        <span className="font-medium">Referencia:</span> {data.referencia}
      </p>
      <p>
        <span className="font-medium">Operación:</span> {data.operacion}
      </p>
    </div>
  );
}

export default Notificacion;
