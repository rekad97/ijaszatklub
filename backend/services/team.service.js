const config = require('../config/config.json');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const db = require('../database/db');

const Team = db.Team;


module.exports = {
    getAll,
    getById,
    create,
    delete: _delete,
};



async function getAll() {
    return await Team.find();
}

function getById(id) {
    return Team.findById(id);
}

async function create(teamParam) {
    if (await Team.findOne({ name: teamParam.name })) {
        throw 'Team name "' + teamParam.name + '" is already taken';
    }
    const team = new Team(teamParam);

    await team.save();
    return team._id;
}

async function _delete(id) {
    await Team.findByIdAndRemove(id);
}