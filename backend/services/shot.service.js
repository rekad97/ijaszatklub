const config = require('../config/config.json');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const db = require('../database/db');

const Shot = db.Shot;
const Training = db.Training;


module.exports = {
    getById,
    create,
    delete: _delete,
};



function getById(id) {
    return Shot.findById(id);
}

async function create(id, shotParam) {
    const shot = new Shot(shotParam);
    shot.trainingId = id;
    await shot.save();
    Training.findByIdAndUpdate(id, { $push: { shots: shot._id } }, { new: true }).then();
    return shot._id;
}

async function _delete(id) {
    await Shot.findByIdAndRemove(id);
}