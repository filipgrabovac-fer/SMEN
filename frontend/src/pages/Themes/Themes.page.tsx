import { PencilIcon, PlusIcon, TrashIcon } from "@heroicons/react/24/solid";
import { Button, Table } from "antd";
import { useState } from "react";
import { CreateThemeModal } from "./components/CreateThemeModal.component";
import { useGetThemes } from "./hooks/useGetThemes.hook";
import { useNavigate } from "@tanstack/react-router";
import { themeOverviewRoute } from "../../routes/theme-overview/theme-overview.routes";
import { EditThemeModal } from "./components/EditThemeModal.component";

export const Themes = () => {
  const [isCreateThemeModalOpen, setIsCreateThemeModalOpen] = useState(false);
  const { data } = useGetThemes();
  const navigate = useNavigate();

  const [selectedThemeId, setSelectedThemeId] = useState<number | undefined>();
  const dataSource = data?.map((theme) => ({
    key: theme.id,
    naziv: theme.title,
    opis: theme.description,
    datum_objave: theme.createdAt.slice(0, 10),
    autor: theme.author,
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
    edit: (
      <div className="flex gap-x-2">
        <Button type="primary" onClick={() => setSelectedThemeId(theme.id)}>
          <PencilIcon className="w-3 h-3" />
        </Button>
        <Button className="p-auto">
          <TrashIcon className="w-4 h-4" color="red" />
        </Button>
        <EditThemeModal
          selectedThemeId={selectedThemeId}
          setSelectedThemeId={setSelectedThemeId}
        />
      </div>
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
    {
      title: "",
      dataIndex: "edit",
      key: "edit",
    },
  ];

  return (
    <div className="w-4/5 m-auto flex flex-col gap-y-6 mt-6">
      <div className="flex justify-between">
        <h1 className="text-2xl font-medium">Teme</h1>
        <Button type="primary" onClick={() => setIsCreateThemeModalOpen(true)}>
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
