const users = []; // This should be replaced with actual database logic

const registerController = {
    register: async (request, h) => {
        const { username, email, password } = request.payload || {}; // Add default empty object
        if (!username || !email || !password) {
            return h.response({ message: 'Username, email, and password are required' }).code(400);
        }
        const user = { id: users.length + 1, username, email, password };
        users.push(user);
        return { message: 'Registration successful' };
    }
};

module.exports = registerController;
