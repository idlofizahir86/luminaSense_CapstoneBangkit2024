const profileController = require('../controllers/controller-profile');

module.exports = [
    {
        method: 'GET',
        path: '/profile',
        handler: profileController.profile
    }
];
