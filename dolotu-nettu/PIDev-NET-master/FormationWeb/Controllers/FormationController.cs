using Data;
using Domain;
using FormationWeb.Models;
using Newtonsoft.Json;
using PagedList;
using Service;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;

namespace FormationWeb.Controllers
{
    public class FormationController : Controller
    {

        private Context db = new Context();
        FormationService FormationService = null;
        ParticipationService ParticipationService = null;
        user currentUser = null;
        public FormationController()
        {
            currentUser = new user();
            currentUser.Id = 2;
            currentUser.adresse = "dahmani";
            FormationService = new FormationService();
            ParticipationService = new ParticipationService();


        }

        // GET: formation
        public ActionResult Index(string sortOrder, string currentFilter, string searchString, int? page)
        {

            ViewBag.NameSortParm = String.IsNullOrEmpty(sortOrder) ? "type_desc" : "";
            ViewBag.DateSortParm = sortOrder == "Date" ? "date_desc" : "Date";


            if (searchString != null)
            {

                page = 1;
            }
            else { searchString = currentFilter; }

            ViewBag.CurrentFilter = searchString;


            var formation = from s in db.formation select s;
            formation = formation.OrderBy(s => s.adresse);
            formation = formation.OrderBy(s => s.description);
            formation = formation.OrderBy(s => s.dateDebut);

            if (!String.IsNullOrEmpty(searchString))
            {
                formation = formation.Where(s =>
        s.adresse.ToUpper().Contains(searchString.ToUpper())
                                       ||
        s.type.ToUpper().Contains(searchString.ToUpper())
                                        ||
        s.description.ToUpper().Contains(searchString.ToUpper())


        );
            }



            int pageSize = 4;
            int pageNumber = (page ?? 1);
            return View(formation.ToPagedList(pageNumber, pageSize));
            //   return RedirectToAction("Index");

        }

        // GET: formation
        public ActionResult Search()
        {
            IEnumerable<Formation> formation = FormationService.GetAllFormationFromNow();
            return View(formation);

        }



        // GET: Home/Reserver/5
        public ActionResult Reserver(int id)
        {

            Participation res = new Participation();
            
            res.Formation.Id = id;
            res.user.Id = currentUser.Id;
            res.date_participation = DateTime.Now;
            res.nombre = 1;
            ParticipationService.AddParticipation(res);
            ParticipationService.SaveChange();

            IEnumerable<Participation> mesParticipations = ParticipationService.GetAllParticipationByUser(currentUser.Id);
            foreach (var part in mesParticipations)
            {
                int x;
                if (part.Formation.Id!=0)
                {
                    x = part.Formation.Id;
                    part.Formation = FormationService.GetFormationById(x);
                }

            }

            return View(mesParticipations);

        }





        // GET: Home/Details/5
        public ActionResult Details(int id)

        {
            if (id == null)

                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            Formation u = FormationService.GetFormationById(id);


            FormationModels lum = new FormationModels

            {type=u.type,
                Id = u.Id,
                dateDebut = u.dateDebut,
                dateFin = u.dateFin,
                prix_unitaire = u.prix_unitaire,
                NbrePlace = u.NbrePlace,
                adresse = u.adresse,
                description = u.description



            };


            if (lum == null)
                return HttpNotFound();

            return View(lum);
        }






        // GET: formation/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: formation/Create
        [HttpPost]
        public ActionResult Create(FormationModels u)
        {
            Formation h = new Formation
            {
                Id = u.Id,
                type=u.type,
                dateDebut = u.dateDebut,
                dateFin = u.dateFin,
                prix_unitaire = u.prix_unitaire,
                NbrePlace = u.NbrePlace,
                adresse = u.adresse,
                description = u.description
            };
            FormationService.AddFormation(h);
            FormationService.SaveChange();
            return RedirectToAction("Create");
        }



        // GET: House/Edit/5
        public ActionResult Edit(int id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Formation c = FormationService.GetFormationById(id);
            if (c == null)
            {
                return HttpNotFound();
            }

            return View(c);
        }

        // POST: House/Edit/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit(Formation c)
        {
            if (ModelState.IsValid)
            {
                FormationService.Update(c);
                return RedirectToAction("Index");
            }
            return View(c);
        }




        // GET: House/Delete/5
        public ActionResult Delete(int id)
        {


            FormationService.DeleteFormation(id);
            var hs = FormationService.GetAllFormation();
            return RedirectToAction("Index", hs);
        }

        // POST: House/Delete/5
        [HttpPost]
        public ActionResult Delete(int id, FormCollection collection)
        {
            try
            {
                // TODO: Add delete logic here

                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }



        [HttpGet]
        public ActionResult ListHouses()
        {
            List<FormationModels> pvm = new List<FormationModels>();
            IEnumerable<Formation> prod = FormationService.GetAllFormation();
            ////var off = c.GetOfferByPROUser(firstname);
            //return View(off);
            foreach (var u in prod)
            {

                FormationModels G = new FormationModels
                {
                    Id = u.Id,
                    type=u.type,
                    dateDebut = u.dateDebut,
                    dateFin = u.dateFin,
                    prix_unitaire = u.prix_unitaire,
                    NbrePlace = u.NbrePlace,
                    adresse = u.adresse,
                    description = u.description


                };

                pvm.Add(G);

            }

            return View(pvm);
        }


        public ActionResult StatTypeFormation(int id)
        {
            //prepare array liste ro c#
            ArrayList Header = new ArrayList { "depart", "Vole" };
            Dictionary<string, int> formstat = ParticipationService.StatFormation(id);
            ArrayList data = new ArrayList { Header };
            foreach (var s in formstat)
            {
                ArrayList d = new ArrayList { s.Key, s.Value };
                data.Add(d);
            }

            //convert it in json
            string datastr = JsonConvert.SerializeObject(data, Formatting.None);
            //sort in viwdata/viewbag
            ViewBag.nbrehouse = ParticipationService.nbreParticipationbyformation(id);
            ViewBag.Data = new HtmlString(datastr);
            return View();
        }



    }
}