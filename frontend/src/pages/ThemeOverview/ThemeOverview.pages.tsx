import { Button, Input, Select, Table } from "antd";
import { WorkshopApplicationModal } from "./components/WorkshopApplicationModal.component";
import { useState } from "react";
import { useGetWorkshopsForTheme } from "./hooks/useGetWorkshopsForTheme.hook";
import { useGetThemeDetails } from "./hooks/useGetThemeDetails.hook";
import { themeOverviewRoute } from "../../routes/theme-overview/theme-overview.routes";
import { PencilIcon, PlusIcon, TrashIcon } from "@heroicons/react/24/solid";
import { CreateWorkshopModal } from "./components/CreateWorkshopModal.component";

import { useQueryClient } from "@tanstack/react-query";
import { useDeleteWorkshop } from "./hooks/useDeleteWorkshop.hook";
import {
  EditWorkshopModal,
  WorkshopStatusEnum,
} from "./components/EditWorkshopModal.component";

const columns = [
  {
    title: "naziv",
    dataIndex: "naziv",
    key: "naziv",
  },
  {
    title: "datum održavanja",
    dataIndex: "datum_odrzavanja",
    key: "datum_odrzavanja",
  },
  {
    title: "broj mjesta",
    dataIndex: "broj_mjesta",
    key: "broj_mjesta",
  },
  {
    title: "opis",
    dataIndex: "opis",
    key: "opis",
  },
  {
    title: "status",
    dataIndex: "status",
    key: "status",
  },
  {
    title: "",
    dataIndex: "find_out_more",
    key: "find_out_more",
  },
  { title: "", dataIndex: "edit", key: "edit" },
];

export const ThemeOverview = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedWorkshopId, setSelectedWorkshopId] = useState<
    number | undefined
  >();
  const [selectedWorkshopTitle, setSelectedWorkshopTitle] = useState<
    string | undefined
  >();
  const [selectedWorkshopDescription, setSelectedWorkshopDescription] =
    useState<string | undefined>();

  const [isCreateWorkshopModalOpen, setIsCreateWorkshopModalOpen] =
    useState(false);

  const [isEditThemeModalOpen, setIsEditThemeModalOpen] = useState<
    boolean | undefined
  >();

  const [workshopNumberOfPlaces, setWorkshopNumberOfPlaces] = useState<
    number | undefined
  >();

  const [selectedWorkshopStatusFilter, setSelectedWorkshopStatusFilter] =
    useState<WorkshopStatusEnum | undefined>();

  const [searchValue, setSearchValue] = useState<string>("");

  const [workshopStatusId, setWorkshopStatusId] = useState<number>(1);

  const { themeId } = themeOverviewRoute.useParams();
  const { data: themeWorkshops } = useGetWorkshopsForTheme({
    subjectId: themeId,
  });
  const { data: themeDetails } = useGetThemeDetails({ subjectId: themeId });
  const queryClient = useQueryClient();

  const { mutate: postDeleteWorkshop } = useDeleteWorkshop({
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ["workshops"] }),
  });

  const isAdmin = localStorage.getItem("userRole") == "ADMIN";
  const canAddWorkshops =
    localStorage.getItem("userRole") == "MENTOR" || isAdmin;

  const canEditWorkshops =
    localStorage.getItem("userRole") == "TEAM LEAD" || isAdmin;

  const filteredData =
    selectedWorkshopStatusFilter || searchValue != ""
      ? themeWorkshops?.filter(
          (workshop) =>
            workshop.workshopStatus === selectedWorkshopStatusFilter ||
            workshop.title
              .toLowerCase()
              .includes(searchValue.toLowerCase().trim())
        )
      : themeWorkshops;

  const dataSource = filteredData?.map((workshop) => ({
    key: workshop.id,
    naziv: workshop.title,
    datum_odrzavanja: workshop.dateOfEvent,
    broj_mjesta: workshop.noOfAvailableSlots,
    opis: workshop.description,
    status: workshop.workshopStatus,
    find_out_more: (
      <p
        className="underline text-blue-900 cursor-pointer"
        onClick={() => {
          setSelectedWorkshopId(workshop.id);
          setIsModalOpen(true);
        }}
      >
        Saznaj više
      </p>
    ),
    ...(canEditWorkshops && {
      edit: (
        <div className="flex gap-x-2">
          <Button
            type="primary"
            onClick={() => {
              setSelectedWorkshopId(workshop.id);
              setSelectedWorkshopTitle(workshop.title);
              setSelectedWorkshopDescription(workshop.description);
              setWorkshopStatusId(workshop.workshopStatusId);
              setWorkshopNumberOfPlaces(workshop.noOfAvailableSlots);
              setIsEditThemeModalOpen(true);
            }}
          >
            <PencilIcon className="w-3 h-3" />
          </Button>
          <Button
            className="p-auto"
            onClick={() => postDeleteWorkshop({ workshopId: workshop.id })}
          >
            <TrashIcon className="w-4 h-4" color="red" />
          </Button>
          <EditWorkshopModal
            workshopStatusId={workshopStatusId}
            isModalOpen={isEditThemeModalOpen}
            setIsModalOpen={setIsEditThemeModalOpen}
            selectedWorkshopId={selectedWorkshopId}
            setSelectedWorkshopId={setSelectedWorkshopId}
            workshopTitle={selectedWorkshopTitle ?? ""}
            workshopDescription={selectedWorkshopDescription ?? ""}
            setWorkshopTitle={setSelectedWorkshopTitle}
            setWorkshopDescription={setSelectedWorkshopDescription}
            setWorkshopStatusId={setWorkshopStatusId}
            setWorkshopNumberOfPlaces={setWorkshopNumberOfPlaces}
            workshopNumberOfPlaces={workshopNumberOfPlaces}
          />
        </div>
      ),
    }),
  }));

  return (
    <>
      <div className="bg-gray-100 flex gap-y-8 p-8 justify-between">
        <div>
          <p className="text-2xl font-medium">{themeDetails?.title}</p>
          <p>{themeDetails?.description}</p>
        </div>
        {canAddWorkshops && (
          <div className="mt-auto">
            <Button
              type="primary"
              onClick={() => setIsCreateWorkshopModalOpen(true)}
            >
              Dodaj radionicu <PlusIcon className="w-5 h-5" />
            </Button>
          </div>
        )}
      </div>

      <div className="flex w-4/5 m-auto gap-x-2 justify-center mt-4">
        <Input
          allowClear
          className="basis-1/2"
          placeholder="pretraži"
          onChange={(e) => setSearchValue(e.target.value)}
        />
        <Select
          className="w-[160px]"
          options={Object.values(WorkshopStatusEnum).map((workshopStatus) => ({
            value: workshopStatus,
            label: workshopStatus,
          }))}
          placeholder="Filtriraj po statusu"
          allowClear
          onClear={() => setSelectedWorkshopStatusFilter(undefined)}
          onSelect={(value) => setSelectedWorkshopStatusFilter(value)}
        />
      </div>

      <Table
        columns={columns}
        dataSource={dataSource}
        className="w-4/5 m-auto mt-[30px]"
      />
      {selectedWorkshopId && (
        <WorkshopApplicationModal
          isModalOpen={isModalOpen}
          setIsModalOpen={setIsModalOpen}
          selectedWorkshopId={selectedWorkshopId}
          // @ts-expect-error random error, works without it
          setSelectedWorkshopId={setSelectedWorkshopId}
        />
      )}

      <CreateWorkshopModal
        isCreateWorkshopModalOpen={isCreateWorkshopModalOpen}
        setIsCreateWorkshopModalOpen={setIsCreateWorkshopModalOpen}
      />
    </>
  );
};
