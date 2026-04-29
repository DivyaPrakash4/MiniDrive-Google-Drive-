import { useEffect, useState } from "react";
import api from "../api/api";
import FileUpload from "../components/FileUpload";

export default function Dashboard({ onLogout }) {
  const [files, setFiles] = useState([]);

  const loadFiles = async () => {
    const res = await api.get("/files");
    setFiles(res.data);
  };

  const downloadFile = async (id) => {
    const res = await api.get(`/files/download/${id}`);
    window.open(res.data, "_blank");
  };

  const deleteFile = async (id) => {
    await api.delete(`/files/${id}`);
    loadFiles();
  };

  useEffect(() => {
    loadFiles();
  }, []);

  return (
    <div className="page">
      <div style={{ display: "flex", justifyContent: "space-between" }}>
        <h2>My Files</h2>
        <button className="danger" onClick={onLogout}>Logout</button>
      </div>

      <FileUpload refresh={loadFiles} />

      <ul className="file-list">
        {files.map(file => (
          <li key={file.id} className="file-item">
            <span>{file.fileName}</span>
            <div>
              <button onClick={() => downloadFile(file.id)}>Download</button>
              <button className="danger" onClick={() => deleteFile(file.id)}>
                Delete
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}
