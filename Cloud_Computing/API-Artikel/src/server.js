import Hapi from '@hapi/hapi';
import routes from './routes/routes.js';

const server = Hapi.server({
  port: process.env.PORT || 9000, 
  host: '0.0.0.0', 
});

const init = async () => {
  server.route(routes);

  await server.start();
  console.log('Server running on %s', server.info.uri);
};

init().catch((err) => {
  console.error('Error starting server:', err);
  process.exit(1);
});
