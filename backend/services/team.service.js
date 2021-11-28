const config = require('../config/config.json');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const db = require('../database/db');
const userService = require('../services/user.service');
const Team = db.Team;
const User = db.User;

module.exports = {
    getAll,
    getById,
    create,
    delete: _delete,
    getTrainingsFromTeamUsers,
    addUser
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
    team.users.push(teamParam.adminId);
    await team.save();
    User.findByIdAndUpdate(team.adminId, { $push: { teams: team._id } }, { new: true }).then();
    return team._id;
}

async function _delete(id) {
    await Team.findByIdAndRemove(id);
}

async function getTrainingsFromTeamUsers(team) {
    let trainings = [];
    console.log(team);
    const users = team.users;
    console.log('users', users.length);
    for (let i = 0; i < users.length; i++) {
        userService.getById(users[i]).then((data) => {
            trainings.push(data.trainings);
            console.log(trainings)
        });
    }

    return trainings;

}

async function addUser(team, userId) {
    userService.getById(userId).then((data) => {
        data.teams.push(team._id)
    })
    team.users.push(userId);

}