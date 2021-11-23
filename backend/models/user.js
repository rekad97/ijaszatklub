const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const userModel = new Schema({
    email: { type: String, unique: true, required: true },
    password: { type: String, required: true },
    teams: [
        { type: mongoose.Schema.Types.ObjectId, ref: 'Team' }
    ]
});

userModel.set('toJSON', {
    virtuals: true,
    versionkey: false,
    transform: function(doc, ret) {
        delete ret._id;
        delete ret.hash;
    }
});

module.exports = mongoose.model('User', userModel);