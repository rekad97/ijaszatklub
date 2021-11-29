const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const trainingModel = new Schema({
    userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
    isIndoor: { type: Boolean },
    setupId: { type: mongoose.Schema.Types.ObjectId, ref: 'Setup', required: true },
    arrowCnt: { type: Number },
    isPublic: { type: Boolean },
    date: { type: Date },
    shots: [
        { type: mongoose.Schema.Types.ObjectId, ref: 'Shot' }
    ]

});

trainingModel.set('toJSON', {
    virtuals: true,
    versionkey: false,
    transform: function(doc, ret) {
        delete ret._id;
    }
});

module.exports = mongoose.model('Training', trainingModel);