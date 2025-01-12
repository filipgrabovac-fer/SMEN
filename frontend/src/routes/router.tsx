import { createRouter } from "@tanstack/react-router";
import { rootRoute } from "./root.routes";
import { anotherHomeRoute, homeRoute } from "./home/home.routes";
import { loginRoute } from "./auth/login.routes";
import { registerRoute } from "./auth/register.routes";

const routeTree = rootRoute.addChildren([
  homeRoute,
  anotherHomeRoute,
  loginRoute,
  registerRoute,
]);

export const router = createRouter({ routeTree });
