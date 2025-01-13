import { PlusIcon } from "@heroicons/react/24/solid";
import { Button, Table } from "antd";
import { useState } from "react";
import { CreateThemeModal } from "./components/CreateThemeModal.component";
import { useGetThemes } from "./hooks/useGetThemes.hook";
import { useNavigate } from "@tanstack/react-router";
import { themeOverviewRoute } from "../../routes/theme-overview/theme-overview.routes";

export const Themes = () => {
  const [isCreateThemeModalOpen, setIsCreateThemeModalOpen] = useState(false);
  const { data } = useGetThemes();
  const navigate = useNavigate();

  const dataSource = data?.map((theme) => ({
    key: theme.id,
    naziv: theme.title,
    opis: theme.description,
    datum_objave: "22-23-2020",
    autor: "Unknown",
    find_out_more: (
      <p
        className="underline text-blue-900 cursor-pointer"
        onClick={() =>
          navigate({
            to: themeOverviewRoute.fullPath,
            params: { themeId: theme.id },
          })
        }
      >
        Saznaj više
      </p>
    ),
  }));

  const columns = [
    {
      title: "naziv",
      dataIndex: "naziv",
      key: "naziv",
    },
    {
      title: "opis",
      dataIndex: "opis",
      key: "opis",
    },
    {
      title: "datum objave",
      dataIndex: "datum_objave",
      key: "datum_objave",
    },
    {
      title: "autor",
      dataIndex: "autor",
      key: "autor",
    },
    {
      title: "",
      dataIndex: "find_out_more",
      key: "find_out_more",
    },
  ];

  return (
    <div className="w-4/5 m-auto flex flex-col gap-y-6 mt-6">
      <div className="flex justify-between">
        <h1 className="text-2xl font-medium">Teme</h1>
        <Button onClick={() => setIsCreateThemeModalOpen(true)}>
          Dodaj temu <PlusIcon className="w-5 h-5" />
        </Button>
      </div>
      <Table dataSource={dataSource} columns={columns} />

      <CreateThemeModal
        isModalOpen={isCreateThemeModalOpen}
        setIsModalOpen={setIsCreateThemeModalOpen}
      />
    </div>
  );
};
