const config = require('../config/config.json');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const db = require('../database/db');

const Shot = db.Shot;


module.exports = {
    getAll,
    getById,
    create,
    delete: _delete,
};


async function getAll() {
    return await Shot.find();
}

function getById(id) {
    return Shot.findById(id);
}

async function create(shotParam) {
    const shot = new Shot(shotParam);
    await shot.save();
    return shot._id;
}

async function _delete(id) {
    await Shot.findByIdAndRemove(id);
}