const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const setupModel = new Schema({
    setupName: { type: String, unique: true, required: true },
    middlePart: { type: String },
    arm: { type: String },
    retice: { type: String },
    stabilizator: { type: String },
    trainingType: { type: String },
    arrow: { type: String },

});

setupModel.set('toJSON', {
    virtuals: true,
    versionkey: false,
    transform: function(doc, ret) {
        delete ret._id;
    }
});

module.exports = mongoose.model('Setup', setupModel);