import { createRoute } from "@tanstack/react-router";
import { rootRoute } from "../root.routes";
import { Home } from "../../pages/Home/Home.pages";

export const homeRoute = createRoute({
  getParentRoute: () => rootRoute,
  path: "/home",
  component: Home,
});
