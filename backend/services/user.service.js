const config = require('../config/config.json');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const db = require('../database/db');

const User = db.User;


module.exports = {
    auth,
    getAll,
    getById,
    create,
    delete: _delete,

};

async function auth({ email, password }) {
    const user = await User.findOne({ email });
    if (user && bcrypt.compareSync(password, user.password)) {
        const token = jwt.sign({ sub: user.id }, config.secret);
        return {
            ...user.toJSON(),
            token
        };
    }
}

async function getAll() {
    return await User.find();
}

function getById(id) {
    return User.findById(id);
}

async function create(userParam) {
    if (await User.findOne({ email: userParam.email })) {
        throw 'Email "' + userParam.email + '" is already taken';
    }
    const user = new User(userParam);
    if (userParam.password) {
        user.password = bcrypt.hashSync(userParam.password, 10);
    }
    await user.save();
    return user._id;
}

async function _delete(id) {
    await User.findByIdAndRemove(id);
}