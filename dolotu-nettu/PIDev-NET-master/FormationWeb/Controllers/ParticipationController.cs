using Data;
using Domain;
using FormationWeb.Models;
using PagedList;
using Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace FormationWeb.Controllers
{
    public class ParticipationController : Controller
    {
      
        private Context db = new Context();
        FormationService Formationservice = null;
        ParticipationService ParticipationService = null;
        user currentUser = null;



        public ParticipationController()
        {
            currentUser = new user();
            currentUser.Id = 2;
            Formationservice = new FormationService();
            ParticipationService = new ParticipationService();


        }
        // GET: Participation
        public ActionResult IndexParticipation(string sortOrder, string currentFilter, string searchString, int? page)
        {

            ViewBag.NameSortParm = String.IsNullOrEmpty(sortOrder) ? "type_desc" : "";
            ViewBag.DateSortParm = sortOrder == "Date" ? "date_desc" : "Date";
            if (searchString != null)
            {

                page = 1;
            }
            else { searchString = currentFilter; }

            ViewBag.CurrentFilter = searchString;


            IEnumerable<Participation> ParticipationFormations = ParticipationService.GetAllParticipationByUser(currentUser.Id);
            ParticipationFormations = ParticipationFormations.OrderBy(s => s.date_participation);


            if (!String.IsNullOrEmpty(searchString))
            {

            }



            int pageSize = 4;
            int pageNumber = (page ?? 1);
            ViewBag.nbreParticipation = ParticipationService.NbreParticipation(currentUser.Id);
            return View(ParticipationFormations.ToPagedList(pageNumber, pageSize));

        }

        // GET: Participation/Details/5
        public ActionResult Details(int id)
        {


            int x;

            Participation u = ParticipationService.GetParticipationById(id);
            if (u.formation_id.HasValue)
            {
                x = u.formation_id.Value;
                u.Formation = Formationservice.GetFormationById(x);
            }

            ParticipationModel lum = new ParticipationModel

            {
                Id = u.Id,
                date_participation = u.date_participation,
                Formation= u.Formation




            };


            if (lum == null)
                return HttpNotFound();

            return View(lum);
        }

        // GET: Participation/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: Participation/Create
        [HttpPost]
        public ActionResult Create(FormCollection collection)
        {
            try
            {
                // TODO: Add insert logic here

                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }

        // GET: Participation/Edit/5
        public ActionResult Edit(int id)
        {
            return View();
        }

        // POST: Participation/Edit/5
        [HttpPost]
        public ActionResult Edit(int id, FormCollection collection)
        {
            try
            {
                // TODO: Add update logic here

                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }

        // GET: Participation/Delete/5
        public ActionResult Delete(int id)
        {


            ParticipationService.DeleteParticipation(id);
            var hs = ParticipationService.GetAllParticipationByUser(currentUser.Id);
            return RedirectToAction("IndexParticipation", hs);
        }

        // POST: House/Delete/5
        [HttpPost]
        public ActionResult Delete(int id, FormCollection collection)
        {
            try
            {

                // TODO: Add delete logic here

                return RedirectToAction("IndexParticipation");
            }
            catch
            {
                return View();
            }
        }


        public ActionResult Search()
        {
            IEnumerable<Formation> formations = Formationservice.GetAllFormationFromNow();
            return View(formations);

        }

     
        public ActionResult ReserverFormation(int id)
        {

            Participation res = new Participation();
          
            res.formation_id = id;
            res.user_id = currentUser.Id;
            res.date_participation = DateTime.Now;
            ParticipationService.AddParticipation(res);
            ParticipationService.SaveChange();


            IEnumerable<Formation> formations = Formationservice.GetAllFormationFromNow();
            return RedirectToAction("Search", formations);
        }

        // POST: House/Delete/5
        [HttpPost]
        public ActionResult ReserverFormation(int id, FormCollection collection)
        {
            try
            {

                // TODO: Add delete logic here

                return RedirectToAction("Search");
            }
            catch
            {
                return View();
            }






        }

        ///////////////////////////////////////////////pdf//////////////////////////////

       
      
        ///////////////////////////////////


    }
}
