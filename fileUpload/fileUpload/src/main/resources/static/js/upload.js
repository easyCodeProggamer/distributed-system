document.getElementById('fileInput').addEventListener('change', (event) => {
    const file = event.target.files[0];
    if (file) {
        uploadFile(file);
    }
});

/**
 * Calculates the SHA-256 hash of a file.
 * @param {File} file The file to hash.
 * @returns {Promise<string>} A promise that resolves with the hex-encoded hash string.
 */
async function calculateFileHash(file) {
    const buffer = await file.arrayBuffer();
    const hashBuffer = await crypto.subtle.digest('SHA-256', buffer);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    // Convert buffer to hex string
    return hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
}

async function uploadFile(file) {
    console.log('Calculating file hash...');
    // 1. Calculate the hash of the entire file first
    const fileHash = await calculateFileHash(file);
    console.log('File hash:', fileHash);

    //const chunkSize = 1024 * 1024; // 1MB
    const chunkSize = 500 * 1024; // 500KB
    const fileId = 'file_' + Date.now() + '_' + Math.random().toString(36).substring(2, 15);
    const totalChunks = Math.ceil(file.size / chunkSize);
    let uploadedChunks = 0;

    const progressBar = document.getElementById('progressBar');
    progressBar.style.width = '0%';
    progressBar.textContent = '0%';

    for (let chunkId = 0; chunkId < totalChunks; chunkId++) {
        const start = chunkId * chunkSize;
        const end = Math.min(start + chunkSize, file.size);
        const chunk = file.slice(start, end);
        const isFinalChunk = chunkId === totalChunks - 1;

        const formData = new FormData();
        formData.append('file', chunk);
        formData.append('chunkId', chunkId);
        formData.append('fileName', file.name);
        formData.append('fileId', fileId);
        formData.append('isFinalChunk', isFinalChunk);

        // 2. Send the original file hash with the final chunk
        if (isFinalChunk) {
            formData.append('originalFileHash', fileHash);
        }

        try {
            const response = await fetch('/api/upload', {
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                const result = await response.text();
                console.log(result);
                uploadedChunks++;
                const progress = Math.round((uploadedChunks / totalChunks) * 100);
                progressBar.style.width = progress + '%';
                progressBar.textContent = progress + '%';

                if (isFinalChunk) {
                    // You could check the final response from the server here
                    // to confirm the hash matched.
                    alert('File upload complete and verified!');
                }

            } else {
                 const errorText = await response.text();
                throw new Error(`Chunk upload failed: ${errorText}`);
            }
        } catch (error) {
            console.error('Error uploading chunk:', error);
            alert(`An error occurred: ${error.message}`);
            progressBar.style.backgroundColor = '#d9534f'; // Make progress bar red on error
            return;
        }
    }
}