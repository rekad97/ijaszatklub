const config = require('../config/config.json');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const db = require('../database/db');

const Training = db.Training;


module.exports = {
    getAll,
    getById,
    create,
    delete: _delete,
};


async function getAll() {
    return await Training.find();
}

function getById(id) {
    return Training.findById(id);
}

async function create(trainingParam) {
    const training = new Training(trainingParam);
    await training.save();
    return training._id;
}

async function _delete(id) {
    await Training.findByIdAndRemove(id);
}