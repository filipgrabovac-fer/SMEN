import { PencilIcon, PlusIcon, TrashIcon } from "@heroicons/react/24/solid";
import { Button, Input, Table } from "antd";
import { useState } from "react";
import { CreateThemeModal } from "./components/CreateThemeModal.component";
import { useGetThemes } from "./hooks/useGetThemes.hook";
import { useNavigate } from "@tanstack/react-router";
import { themeOverviewRoute } from "../../routes/theme-overview/theme-overview.routes";
import { EditThemeModal } from "./components/EditThemeModal.component";
import { useDeleteTheme } from "./hooks/useDeleteTheme.hook";
import { useQueryClient } from "@tanstack/react-query";

export const Themes = () => {
  const [isCreateThemeModalOpen, setIsCreateThemeModalOpen] = useState(false);
  const { data } = useGetThemes();
  const navigate = useNavigate();

  const queryClient = useQueryClient();
  const { mutate: postDeleteTheme } = useDeleteTheme({
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ["themes"] }),
  });
  const [selectedThemeId, setSelectedThemeId] = useState<number | undefined>();
  const [selectedThemeTitle, setSelectedThemeTitle] = useState<
    string | undefined
  >();
  const [selectedThemeDescription, setSelectedThemeDescription] = useState<
    string | undefined
  >();

  const [isEditThemeModalOpen, setIsEditThemeModalOpen] = useState(false);

  const [searchValue, setSearchValue] = useState("");

  const filteredData =
    searchValue != ""
      ? data?.filter((theme) =>
          theme.title.toLowerCase().includes(searchValue.toLowerCase().trim())
        )
      : data;

  const canEditThemes = localStorage.getItem("userRole") != "USER";

  const dataSource = filteredData?.map((theme) => ({
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
    ...(canEditThemes
      ? {
          edit: (
            <div className="flex gap-x-2">
              <Button
                type="primary"
                onClick={() => {
                  setSelectedThemeTitle(theme.title);
                  setSelectedThemeDescription(theme.description);
                  setSelectedThemeId(theme.id);
                  setIsEditThemeModalOpen(true);
                }}
              >
                <PencilIcon className="w-3 h-3" />
              </Button>
              <Button
                className="p-auto"
                onClick={() => postDeleteTheme({ themeId: theme.id })}
              >
                <TrashIcon className="w-4 h-4" color="red" />
              </Button>
            </div>
          ),
        }
      : undefined),
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
    canEditThemes
      ? {
          title: "",
          dataIndex: "edit",
          key: "edit",
        }
      : {},
  ];

  return (
    <div className="w-4/5 m-auto flex flex-col gap-y-6 mt-6">
      <div className="flex justify-between">
        <h1 className="text-2xl font-medium">Teme</h1>
      </div>
      <div className="flex justify-center gap-x-2">
        <Input
          className="basis-1/2"
          placeholder="pretraži"
          onChange={(e) => setSearchValue(e.target.value)}
        />
        {canEditThemes && (
          <Button
            type="primary"
            onClick={() => setIsCreateThemeModalOpen(true)}
          >
            Dodaj temu <PlusIcon className="w-5 h-5" />
          </Button>
        )}
      </div>
      <Table dataSource={dataSource} columns={columns} />

      <CreateThemeModal
        isModalOpen={isCreateThemeModalOpen}
        setIsModalOpen={setIsCreateThemeModalOpen}
      />
      <EditThemeModal
        title={selectedThemeTitle ?? ""}
        setTitle={setSelectedThemeTitle}
        setDescription={setSelectedThemeDescription}
        description={selectedThemeDescription ?? ""}
        isModalOpen={isEditThemeModalOpen}
        setIsModalOpen={setIsEditThemeModalOpen}
        selectedThemeId={selectedThemeId}
        setSelectedThemeId={setSelectedThemeId}
      />
    </div>
  );
};
