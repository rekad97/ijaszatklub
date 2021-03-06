const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const userModel = new Schema({
    email: { type: String, unique: true, required: true },
    password: { type: String, required: true },
    teams: [
        { type: mongoose.Schema.Types.ObjectId, ref: 'Team' }
    ],
    trainings: [
        { type: mongoose.Schema.Types.ObjectId, ref: 'Training' }
    ],
    setups: [
        { type: mongoose.Schema.Types.ObjectId, ref: 'Setup' }

    ],
    bestPoint: { type: Number }
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