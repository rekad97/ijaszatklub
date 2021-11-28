const config = require('../config/config.json');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const db = require('../database/db');

const Training = db.Training;
const User = db.User;


module.exports = {
    getById,
    create,
    delete: _delete,
    deleteShots
};



function getById(id) {
    return Training.findById(id);
}

async function create(id, trainingParam) {
    const training = new Training(trainingParam);
    training.userId = id;
    await training.save();
    User.findByIdAndUpdate(id, { $push: { trainings: training._id } }, { new: true }).then();
    return training._id;
}

async function _delete(id) {
    await Training.findByIdAndRemove(id);
}

async function deleteShots(id) {
    console.log(id);
    let newShots = [];
    return Training.findByIdAndUpdate(
        id, { $set: { shots: newShots } }, { new: true }).then();
}