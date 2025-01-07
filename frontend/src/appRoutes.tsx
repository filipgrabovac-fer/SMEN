import { RouteObject } from "react-router";
import AppLayout from "./layout/AppLayout";

export const appRoutes: RouteObject[] = [
  {
    path: "/",
    element: <AppLayout />,
    children: [
      { index: true, element: <>sdgadfh</> },
      { path: "user", element: "asdf" },
    ],
  },
];
