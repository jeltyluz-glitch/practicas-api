const express = require('express');
const { Database } = require('@sqlitecloud/drivers');
const cors = require('cors');

const app = express();
app.use(express.json());
app.use(cors());

// Conexión a SQLite Cloud para la base de datos "Favoritos"
// Recuerda reemplazar TU_PASSWORD con tu contraseña real de SQLite Cloud
const db = new Database("sqlitecloud://admin:JGtfiOc7O94WoX63@caqqbdkqdk.g4.sqlite.cloud:8860/Favoritos?tls=true");

// 1. Endpoint para obtener personajes
app.get('/personajes', async (req, res) => {
    try {
        const result = await db.sql("SELECT * FROM personajes;");
        res.json(result);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

// 2. Endpoint para actualizar favoritos
app.put('/personajes/:id/favorito', async (req, res) => {
    const { id } = req.params;
    const { is_favorite } = req.body;
    try {
        await db.sql(`UPDATE personajes SET is_favorite = ${is_favorite ? 1 : 0} WHERE id = ${id};`);
        res.json({ success: true });
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

// 3. Endpoint para sincronizar personajes desde la app
app.post('/personajes/sync', async (req, res) => {
    const personajes = req.body;
    try {
        for (const p of personajes) {
            await db.sql(`INSERT OR IGNORE INTO personajes (id, nombre, estado, especie, imagen, is_favorite) VALUES (${p.id}, '${p.nombre}', '${p.estado}', '${p.especie}', '${p.imagen}', 0);`);
        }
        res.json({ success: true });
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Servidor backend corriendo en http://localhost:${PORT}`);
});
