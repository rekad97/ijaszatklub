const config = require('../config/config.json');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const db = require('../database/db');

const Training = db.Training;
const User = db.User;
const Shot = db.Shot;


module.exports = {
    getById,
    getAll,
    create,
    delete: _delete,
    deleteShots,
    addShots,
    updateTraining,
};


function getById(id) {
    return Training.findById(id);
}

async function getAll() {
    return await Training.find();
}



async function create(trainingParam) {
    const training = new Training(trainingParam);
    await training.save();
    User.findByIdAndUpdate(training.userId, { $push: { trainings: training._id } }, { new: true }).then();
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

async function addShots(trainingId, shotId) {
    Training.findByIdAndUpdate(trainingId, { $push: { shots: shotId } }, { new: true }).then();
    Shot.findByIdAndUpdate(shotId, { $set: { trainingId: trainingId } }, { new: true }).then();

}

async function updateTraining(id, trainingParam) {
    const training = await Training.findById(id);
    Object.assign(training, trainingParam);
    await training.save();
}