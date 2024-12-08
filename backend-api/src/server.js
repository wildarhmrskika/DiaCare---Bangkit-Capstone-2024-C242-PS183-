import Hapi from "@hapi/hapi"; // Menggunakan import untuk Hapi
import routes from "./routes.js"; // Tambahkan ekstensi .js

const init = async () => {
  const server = Hapi.server({
    port: 9000,
    host: "0.0.0.0",
    routes: {
      cors: {
        origin: ["*"],
      },
    },
  });

  server.route(routes);

  await server.start();
  console.log(`Server berjalan pada ${server.info.uri}`);
};

init();