export interface ICertification {
  id?: number;
  name?: string;
  description?: string | null;
  creaDate?: Date | null;
  updateDate?: Date | null;
  expireDate?: Date | null;
  notes?: string | null;
}

export class Certification implements ICertification {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string | null,
    public creaDate?: Date | null,
    public updateDate?: Date | null,
    public expireDate?: Date | null,
    public notes?: string | null,
  ) {}
}
