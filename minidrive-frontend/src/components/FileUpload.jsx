import api from "../api/api";

export default function FileUpload({ refresh }) {
  const uploadFile = async (e) => {
    const file = e.target.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file);

    await api.post("/files/upload", formData, {
      headers: { "Content-Type": "multipart/form-data" }
    });

    refresh();
  };

  return (
    <div className="upload-box">
      <input type="file" onChange={uploadFile} />
    </div>
  );
}
