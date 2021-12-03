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
    getShotScore
};



function getById(id) {
    return Shot.findById(id);
}

async function create(shotParam) {
    const shot = new Shot(shotParam);
    await shot.save();
    Training.findByIdAndUpdate(shot.trainingId, { $push: { shots: shot._id } }, { new: true }).then();
    return shot._id;
}

async function _delete(id) {
    await Shot.findByIdAndRemove(id);
}

async function getShotScore(id) {
    const shot = await Shot.findById(id);
    return shot.score;
}