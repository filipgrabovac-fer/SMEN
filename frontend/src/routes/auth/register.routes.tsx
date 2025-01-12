import { createRoute } from "@tanstack/react-router";
import { rootRoute } from "../root.routes";
import { Register } from "../../pages/Auth/Register.pages";

export const registerRoute = createRoute({
  getParentRoute: () => rootRoute,
  path: "/register",
  component: Register,
});
