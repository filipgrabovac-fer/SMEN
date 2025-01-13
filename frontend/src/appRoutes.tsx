import { RouteObject } from "react-router";
import AppLayout from "./layout/AppLayout";
import LayoutPregledOglasa from "./features/pregledOglasa/LayoutPregledOglasa";

export const appRoutes: RouteObject[] = [
  {
    path: "/",
    element: <AppLayout />,
    children: [{ index: true, element: <LayoutPregledOglasa /> }],
  },
];
