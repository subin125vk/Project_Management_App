import React, { useState } from 'react';
import axios from 'axios';

function ExportToGist() {
  const [projectId, setProjectId] = useState('');
  const [status, setStatus] = useState(null);
  const [error, setError] = useState(null);

  const handleExport = async () => {
    try {
      const response = await axios.post(
        `${process.env.REACT_APP_API_BASE_URL}/projects/exportToGist/${projectId}`
      );
      setStatus(`Gist created successfully! View it here: ${response.data}`);
      setError(null);
    } catch (error) {
      setError('Failed to export to Gist. Please try again.');
      setStatus(null);
    }
  };

  return (
    <div>
      <h2>Export Project to Gist</h2>
      <input 
        type="text" 
        value={projectId} 
        onChange={(e) => setProjectId(e.target.value)} 
        placeholder="Enter Project ID" 
      />
      <button onClick={handleExport}>Export to Gist</button>
      {status && <p>{status}</p>}
      {error && <p>{error}</p>}
    </div>
  );
}

export default ExportToGist;
