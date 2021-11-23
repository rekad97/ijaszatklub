const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const teamModel = new Schema({
    name: { type: String, unique: true, required: true },
    adminId: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
    users: [
        { type: mongoose.Schema.Types.ObjectId, ref: 'User' }
    ]
});

teamModel.set('toJSON', {
    virtuals: true,
    versionkey: false,
    transform: function(doc, ret) {
        delete ret._id;
    }
});

module.exports = mongoose.model('Team', teamModel);