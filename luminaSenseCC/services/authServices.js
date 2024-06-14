const jwt = require('jsonwebtoken');

const generateToken = (user) => {
    const payload = {
        user: {
            id: user.id,
            name: user.name,
            email: user.email
        }
    };
    return jwt.sign(payload, "s3cr3t_g4m1ng_123", { expiresIn: '1h' });
};

module.exports = {generateToken}
