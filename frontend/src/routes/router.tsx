import { createRouter } from "@tanstack/react-router";
import { rootRoute } from "./root.routes";
import { homeRoute } from "./home/home.routes";
import { loginRoute } from "./auth/login.routes";
import { registerRoute } from "./auth/register.routes";
import { themesRoute } from "./themes/themes.routes";
import { themeOverviewRoute } from "./theme-overview/theme-overview.routes";
import { mentorsRoute } from "./mentors/mentors.routes";
import { workshopsRoute } from "./workshops/workshops.route";
import { oglasRoute } from "./oglas/oglas.routes";

const routeTree = rootRoute.addChildren([
  homeRoute,
  loginRoute,
  registerRoute,
  themesRoute,
  themeOverviewRoute,
  mentorsRoute,
  workshopsRoute,
  oglasRoute,
]);

export const router = createRouter({ routeTree });
