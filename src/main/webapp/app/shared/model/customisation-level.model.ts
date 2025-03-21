export interface ICustomisationLevel {
  id?: number;
  level?: string | null;
  creaDate?: Date | null;
  updateDate?: Date | null;
  notes?: string | null;
}

export class CustomisationLevel implements ICustomisationLevel {
  constructor(
    public id?: number,
    public level?: string | null,
    public creaDate?: Date | null,
    public updateDate?: Date | null,
    public notes?: string | null,
  ) {}
}
