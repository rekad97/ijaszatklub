const config = require('../config/config.json');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const db = require('../database/db');

const Team = db.Team;
const User = db.User;

module.exports = {
    getAll,
    getById,
    create,
    delete: _delete,
    getTrainingsFromTeamUsers
};



async function getAll() {
    return await Team.find();
}

function getById(id) {
    return Team.findById(id);
}

async function create(id, teamParam) {
    if (await Team.findOne({ name: teamParam.name })) {
        throw 'Team name "' + teamParam.name + '" is already taken';
    }
    const team = new Team(teamParam);
    team.users.push(id);
    await team.save();
    User.findByIdAndUpdate(id, { $push: { teams: team._id } }, { new: true }).then();
    return team._id;
}

async function _delete(id) {
    await Team.findByIdAndRemove(id);
}

async function getTrainingsFromTeamUsers(id) {
    const team = getById(id);
    const users = team.users;
    for (let i = 0; i < users.length; i++) {
        const user = getById(users[i]);
        return user.trainings;
    }
}