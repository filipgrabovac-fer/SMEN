import {
  createRootRoute,
  createRoute,
  Outlet,
  redirect,
} from "@tanstack/react-router";
import GlobalHeader from "../components/GlobalHeader";
import { Layout } from "antd";
import { loginRoute } from "./auth/login.routes";

export const rootRoute = createRootRoute({
  component: () => (
    <Layout className="layout" style={{ backgroundColor: "#ffffffff" }}>
      <GlobalHeader />
      <Outlet />
    </Layout>
  ),
});

export const defaultRoute = createRoute({
  getParentRoute: () => rootRoute,
  path: "/",
  beforeLoad: async () => {
    const token = localStorage.getItem("token");

    if (token == null) {
      return redirect({ to: loginRoute.fullPath });
    }
    return redirect({ to: "/themes" });
  },
});
