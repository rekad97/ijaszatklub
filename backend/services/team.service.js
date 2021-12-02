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
    addUser,
    deleteUser
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

async function getTrainingsFromTeamUsers(id) {
    const team = await Team.findById(id);
    let trainings = [];
    const users = team.users;
    console.log('users', users.length);
    users.forEach(async(userId) => {
        const user = await User.findById(userId);
        await trainings.push(user.trainings);
        console.log(trainings)
    })

    return await trainings;


}

async function addUser(teamId, userId) {
    User.findByIdAndUpdate(userId, { $push: { teams: teamId } }, { new: true }).then();
    Team.findByIdAndUpdate(teamId, { $push: { users: userId } }, { new: true }).then();

}

async function deleteUser(teamId, userId) {
    User.findByIdAndUpdate(userId, { $pull: { teams: teamId } }, { new: true }).then();
    Team.findByIdAndUpdate(teamId, { $pull: { users: userId } }, { new: true }).then();

}