const config = require('../config/config.json');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const db = require('../database/db');

const Setup = db.Setup;


module.exports = {
    getAll,
    getById,
    create,
    delete: _delete,
};


async function getAll() {
    return await Setup.find();
}

function getById(id) {
    return Setup.findById(id);
}

async function create(setupParam) {
    if (await Setup.findOne({ setupName: setupParam.setupName })) {
        throw 'SetupName "' + setupParam.setupName + '" is already taken';
    }
    const setup = new Setup(setupParam);

    await setup.save();
}

async function _delete(id) {
    await Setup.findByIdAndRemove(id);
}