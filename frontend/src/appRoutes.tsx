import { RouteObject } from "react-router";
import AppLayout from "./layout/AppLayout";
import LayoutPregledOglasa from "./features/pregledOglasa/layout/LayoutPregledOglasa";
import LayoutPregledTema from "./features/pregledTema/layout/LayoutPregledTema";

export const appRoutes: RouteObject[] = [
  {
    path: "/",
    element: <AppLayout />,
    children: [
      { index: true, element: <LayoutPregledOglasa /> },
      { path: "tema", element: <LayoutPregledTema /> },
    ],
  },
];
